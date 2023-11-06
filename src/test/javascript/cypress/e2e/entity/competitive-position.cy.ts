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

describe('CompetitivePosition e2e test', () => {
  const competitivePositionPageUrl = '/competitive-position';
  const competitivePositionPageUrlPattern = new RegExp('/competitive-position(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const competitivePositionSample = { value: 'Organized Movies', language: 'ENGLISH' };

  let competitivePosition;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/competitive-positions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/competitive-positions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/competitive-positions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (competitivePosition) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/competitive-positions/${competitivePosition.id}`,
      }).then(() => {
        competitivePosition = undefined;
      });
    }
  });

  it('CompetitivePositions menu should load CompetitivePositions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('competitive-position');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CompetitivePosition').should('exist');
    cy.url().should('match', competitivePositionPageUrlPattern);
  });

  describe('CompetitivePosition page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(competitivePositionPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CompetitivePosition page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/competitive-position/new$'));
        cy.getEntityCreateUpdateHeading('CompetitivePosition');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitivePositionPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/competitive-positions',
          body: competitivePositionSample,
        }).then(({ body }) => {
          competitivePosition = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/competitive-positions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/competitive-positions?page=0&size=20>; rel="last",<http://localhost/api/competitive-positions?page=0&size=20>; rel="first"',
              },
              body: [competitivePosition],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(competitivePositionPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CompetitivePosition page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('competitivePosition');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitivePositionPageUrlPattern);
      });

      it('edit button click should load edit CompetitivePosition page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CompetitivePosition');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitivePositionPageUrlPattern);
      });

      it('edit button click should load edit CompetitivePosition page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CompetitivePosition');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitivePositionPageUrlPattern);
      });

      it('last delete button click should delete instance of CompetitivePosition', () => {
        cy.intercept('GET', '/api/competitive-positions/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('competitivePosition').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitivePositionPageUrlPattern);

        competitivePosition = undefined;
      });
    });
  });

  describe('new CompetitivePosition page', () => {
    beforeEach(() => {
      cy.visit(`${competitivePositionPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CompetitivePosition');
    });

    it('should create an instance of CompetitivePosition', () => {
      cy.get(`[data-cy="value"]`).type('back').should('have.value', 'back');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        competitivePosition = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', competitivePositionPageUrlPattern);
    });
  });
});
