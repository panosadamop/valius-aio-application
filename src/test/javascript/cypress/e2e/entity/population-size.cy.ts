import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('PopulationSize e2e test', () => {
  const populationSizePageUrl = '/population-size';
  const populationSizePageUrlPattern = new RegExp('/population-size(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const populationSizeSample = { value: 'transmit bus', language: 'GREEK' };

  let populationSize;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/population-sizes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/population-sizes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/population-sizes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (populationSize) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/population-sizes/${populationSize.id}`,
      }).then(() => {
        populationSize = undefined;
      });
    }
  });

  it('PopulationSizes menu should load PopulationSizes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('population-size');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PopulationSize').should('exist');
    cy.url().should('match', populationSizePageUrlPattern);
  });

  describe('PopulationSize page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(populationSizePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PopulationSize page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/population-size/new$'));
        cy.getEntityCreateUpdateHeading('PopulationSize');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', populationSizePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/population-sizes',
          body: populationSizeSample,
        }).then(({ body }) => {
          populationSize = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/population-sizes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/population-sizes?page=0&size=20>; rel="last",<http://localhost/api/population-sizes?page=0&size=20>; rel="first"',
              },
              body: [populationSize],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(populationSizePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PopulationSize page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('populationSize');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', populationSizePageUrlPattern);
      });

      it('edit button click should load edit PopulationSize page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PopulationSize');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', populationSizePageUrlPattern);
      });

      it('edit button click should load edit PopulationSize page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PopulationSize');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', populationSizePageUrlPattern);
      });

      it('last delete button click should delete instance of PopulationSize', () => {
        cy.intercept('GET', '/api/population-sizes/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('populationSize').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', populationSizePageUrlPattern);

        populationSize = undefined;
      });
    });
  });

  describe('new PopulationSize page', () => {
    beforeEach(() => {
      cy.visit(`${populationSizePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PopulationSize');
    });

    it('should create an instance of PopulationSize', () => {
      cy.get(`[data-cy="value"]`).type('Pants').should('have.value', 'Pants');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        populationSize = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', populationSizePageUrlPattern);
    });
  });
});
