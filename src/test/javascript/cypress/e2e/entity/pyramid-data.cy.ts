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

describe('PyramidData e2e test', () => {
  const pyramidDataPageUrl = '/pyramid-data';
  const pyramidDataPageUrlPattern = new RegExp('/pyramid-data(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const pyramidDataSample = { identifier: 'web-readiness fuchsia', category: 20119, value: 'Garden array Cambridgeshire', order: 70071 };

  let pyramidData;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/pyramid-data+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/pyramid-data').as('postEntityRequest');
    cy.intercept('DELETE', '/api/pyramid-data/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (pyramidData) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/pyramid-data/${pyramidData.id}`,
      }).then(() => {
        pyramidData = undefined;
      });
    }
  });

  it('PyramidData menu should load PyramidData page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('pyramid-data');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PyramidData').should('exist');
    cy.url().should('match', pyramidDataPageUrlPattern);
  });

  describe('PyramidData page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(pyramidDataPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PyramidData page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/pyramid-data/new$'));
        cy.getEntityCreateUpdateHeading('PyramidData');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', pyramidDataPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/pyramid-data',
          body: pyramidDataSample,
        }).then(({ body }) => {
          pyramidData = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/pyramid-data+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/pyramid-data?page=0&size=20>; rel="last",<http://localhost/api/pyramid-data?page=0&size=20>; rel="first"',
              },
              body: [pyramidData],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(pyramidDataPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PyramidData page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('pyramidData');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', pyramidDataPageUrlPattern);
      });

      it('edit button click should load edit PyramidData page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PyramidData');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', pyramidDataPageUrlPattern);
      });

      it('edit button click should load edit PyramidData page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PyramidData');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', pyramidDataPageUrlPattern);
      });

      it('last delete button click should delete instance of PyramidData', () => {
        cy.intercept('GET', '/api/pyramid-data/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('pyramidData').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', pyramidDataPageUrlPattern);

        pyramidData = undefined;
      });
    });
  });

  describe('new PyramidData page', () => {
    beforeEach(() => {
      cy.visit(`${pyramidDataPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PyramidData');
    });

    it('should create an instance of PyramidData', () => {
      cy.get(`[data-cy="identifier"]`).type('fuchsia').should('have.value', 'fuchsia');

      cy.get(`[data-cy="category"]`).type('19188').should('have.value', '19188');

      cy.get(`[data-cy="value"]`).type('Officer content').should('have.value', 'Officer content');

      cy.get(`[data-cy="order"]`).type('11114').should('have.value', '11114');

      cy.setFieldImageAsBytesOfEntity('img', 'integration-test.png', 'image/png');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        pyramidData = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', pyramidDataPageUrlPattern);
    });
  });
});
